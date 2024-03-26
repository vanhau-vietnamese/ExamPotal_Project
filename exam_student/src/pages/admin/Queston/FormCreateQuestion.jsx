import { useForm } from 'react-hook-form';
import { Button } from '~/components';
import PropTypes from 'prop-types';
import { useState } from 'react';
import QuestionType from './QuestionType';
import { axiosClient } from '~/apis';
import { FormTextEditor } from '~/components/Form';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormCreateQuestionInput } from '~/validations';

export default function FormCreateQuestion({ onClose }) {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(FormCreateQuestionInput),
    mode: 'onBlur',
  });
  //const [question, setQuestion] = useState([]);
  const [options, setOptions] = useState([]); // lưu giá trị các lựa chọn loại đáp án - thêm /xóa
  const [answerType, setAnswerType] = useState();
  const [selectedAnswer, setSelectedAnswer] = useState([]); // lưu giá trị đáp án đúng đã chọn
  const [error, setError] = useState(''); // kiểm tra khi submit có chọn đáp án chưa

  // thêm đáp án
  function addOption() {
    const newOptions = [...options];
    newOptions.push('');
    setOptions(newOptions);
  }
  // xóa đáp án
  function deleteOption(index) {
    const newOptions = [...options];
    newOptions.splice(index, 1);
    setOptions(newOptions);
  }

  // xử lý chọn đáp án
  const handleAnswerChange = (index) => {
    if (answerType.displayName === 'Đơn') {
      // Nếu đang ở chế độ chọn đơn, chỉ lưu một đáp án
      setSelectedAnswer([index]);
    } else {
      // Nếu đang ở chế độ chọn đa, xử lý lưu nhiều đáp án
      const answerExists = selectedAnswer.includes(index);

      if (answerExists) {
        // Nếu đáp án đã chọn đã tồn tại, loại bỏ nó khỏi mảng
        const newSelectedAnswers = selectedAnswer.filter((answerIndex) => answerIndex !== index);
        setSelectedAnswer(newSelectedAnswers);
      } else {
        // Nếu đáp án đã chọn chưa tồn tại, thêm đáp án vào mảng
        const newSelectedAnswers = [...selectedAnswer, index];
        setSelectedAnswer(newSelectedAnswers);
      }
    }
  };

  //

  const onSubmit = async (data) => {
    // Kiểm tra điều kiện dựa trên loại câu hỏi và số lượng đáp án đã chọn
    let result;
    if (answerType.displayName === 'Đơn' && selectedAnswer.length === 1) {
      result = [options[selectedAnswer[0]]];
      console.log('ĐƠN', result);
      setError(null);
    } else if (answerType.displayName === 'Đa' && selectedAnswer.length >= 2) {
      result = selectedAnswer.map((i) => options[i]);
      console.log('ĐA', result);
      setError(null);
    } else {
      // Không đáp ứng điều kiện, hiển thị thông báo lỗi
      setError(() => {
        alert('Vui lòng chọn đáp án hợp lệ!');
      });
      console.log('Lỗi: ', error);
      return;
    }

    // oject content + correct
    const resultObj = options.map((o) => ({
      content: o,
      correct: result.includes(o),
    }));
    console.log('obj', resultObj);

    //xử lý API
    try {
      // Gửi dữ liệu form đến máy chủ sử dụng Axios
      const response = await axiosClient.post('/question/add', {
        content: data.content, // questionContent
        questionTypeId: answerType.alias, // questionType
        quizId: null,
        answerRequestList: resultObj,
      });

      // Xử lý kết quả từ server nếu cần
      console.log('Response:', response.data);

      // reset form sau khi tạo thành công
      reset({ content: '' });
      setOptions([]);
      setSelectedAnswer([]);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div>
      <div className="flex items-center justify-center pt-6">
        <div className="container mx-auto p-4 bg-slate-100 rounded-md mt-10 max-w-[650px]">
          <h3 className="mb-5">Tạo câu hỏi</h3>
          <form onSubmit={handleSubmit(onSubmit)} className="w-full">
            <div>
              <div className="w-full md:w-1/3 px-3 mb-4 md:mb-0">
                <p>Nhập nội dung câu hỏi</p>
                <FormTextEditor control={control} name="content" error={errors.content?.message} />
              </div>
              <p className="ml-3">Chọn loại đáp án cho câu hỏi</p>
              <div className="ml-3">
                <QuestionType onChange={setAnswerType} />
              </div>

              {/* jsx cho sự kiện chọn đơn thì cho chọn 1 đáp án, chọn đa thì cho chọn nhiều đáp án */}
              <div className="mb-4 ml-3">
                {options &&
                  options.map((option, index) => (
                    <div key={index} className="flex mb-2 items-center">
                      {answerType && answerType.displayName === 'Đơn' ? (
                        <input
                          type="radio"
                          name="answerRadio"
                          className="mr-2"
                          onClick={() => handleAnswerChange(index)}
                        />
                      ) : (
                        <input
                          type="checkbox"
                          className="mr-2"
                          onClick={() => handleAnswerChange(index)}
                        />
                      )}

                      <input
                        required={true}
                        name="answer"
                        type="text"
                        value={option}
                        onChange={(event) => {
                          const newOptions = [...options];
                          newOptions[index] = event.target.value;
                          setOptions(newOptions);
                        }}
                        className="px-3 py-2 mr-2 mt-3 w-full border border-gray-300 rounded-md outline-none focus:border-green-500"
                      />

                      <button
                        type="button"
                        onClick={() => deleteOption(index)} //index để biết vị trí xóa
                        className="px-3 py-2 ml-1 mt-3 text-sm text-black bg-transparent border border-red-500 rounded-md hover:bg-red-500 hover:text-white"
                      >
                        Xóa
                      </button>
                    </div>
                  ))}
                <button
                  type="button"
                  onClick={addOption}
                  className="px-3 py-2 mt-3 text-sm text-black bg-transparent border border-blue-500 rounded-md hover:bg-blue-500 hover:text-white"
                >
                  Thêm đáp án
                </button>
              </div>
            </div>

            <div className="flex justify-center">
              <Button
                type="submit"
                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 w-32 h-12 text-sm rounded m-1"
              >
                Tạo câu hỏi
              </Button>
              <Button
                type="button"
                onClick={onClose}
                className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 w-28 h-12 text-sm rounded m-1"
              >
                Thoát
              </Button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
FormCreateQuestion.propTypes = {
  onClose: PropTypes.func,
};
