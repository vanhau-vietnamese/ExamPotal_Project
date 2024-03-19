import PropTypes from 'prop-types';
import TextEditor from '~/components/TextEditor/TextEditor';
import { Button } from '~/components';
import { useState } from 'react';
import { useRef } from 'react';
import { useEffect } from 'react';
import axios from 'axios';

FormCreateQuestion.propTypes = {
  onClose: PropTypes.func,
};

const useForm = () => {
  const [question, setQuestion] = useState('');
  const [options, setOptions] = useState([]);
  const [correctOptions, setCorrectOptions] = useState([]);
  const [answerType, setAnswerType] = useState('single');
  const optionInputRefs = useRef([]);

  useEffect(() => {
    if (optionInputRefs.current.length > 0) {
      const lastIndex = optionInputRefs.current.length - 1;
      const lastOptionInputRef = optionInputRefs.current[lastIndex];

      if (lastOptionInputRef && lastOptionInputRef.current) {
        lastOptionInputRef.current.focus();
      }
    }
  }, [options]);

  const handleAddOption = () => {
    setOptions([...options, '']);
  };

  const handleOptionChange = (index, value) => {
    const updatedOptions = [...options];
    updatedOptions[index] = value;
    setOptions(updatedOptions);
  };

  const handleToggleCorrectOption = (index) => {
    if (answerType === 'single') {
      // Nếu loại đáp án là "Đơn", chỉ cho phép chọn một lựa chọn
      setCorrectOptions([index]);
    } else {
      // Nếu loại đáp án là "Đa", cho phép chọn nhiều lựa chọn
      if (correctOptions.includes(index)) {
        const updatedCorrectOptions = correctOptions.filter((option) => option !== index);
        setCorrectOptions(updatedCorrectOptions);
      } else {
        setCorrectOptions([...correctOptions, index]);
      }
    }
  };

  const handleRemoveOption = (index) => {
    const updatedOptions = [...options];
    updatedOptions.splice(index, 1);
    setOptions(updatedOptions);
    const updatedCorrectOptions = correctOptions.filter((option) => option !== index);
    setCorrectOptions(updatedCorrectOptions);
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleAddOption();
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/question/add', {
        question: question,
        options: options,
        correctOptions: correctOptions,
        answerType: answerType,
      });

      // Xử lý phản hồi từ backend (nếu cần)
      console.log('Response:', response.data);

      // Xóa dữ liệu form sau khi đã gửi thành công
      setQuestion('');
      setOptions([]);
      setCorrectOptions([]);
      setAnswerType('single');
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return {
    question,
    setQuestion,
    options,
    correctOptions,
    answerType,
    setAnswerType,
    optionInputRefs,
    handleAddOption,
    handleOptionChange,
    handleToggleCorrectOption,
    handleRemoveOption,
    handleKeyDown,
    handleSubmit,
  };
};

export default function FormCreateQuestion({ onClose }) {
  const {
    question,
    setQuestion,
    options,
    correctOptions,
    answerType,
    setAnswerType,
    optionInputRefs,
    handleAddOption,
    handleOptionChange,
    handleToggleCorrectOption,
    handleRemoveOption,
    handleKeyDown,
    handleSubmit,
  } = useForm();

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="flex items-center justify-center bg-slate-50 w-full max-w-[800px] rounded-xl ">
        <form onSubmit={handleSubmit} className="max-w-[700px] mx-auto p-5 w-full">
          <div className="mb-4">
            <label htmlFor="question" className="block mb-2 font-medium text-black">
              Nhập câu hỏi
            </label>

            <TextEditor value={question} onChange={setQuestion} />
          </div>

          <div className="mb-4">
            <label className="block mb-2 font-medium text-black">Loại đáp án</label>
            <select
              value={answerType}
              onChange={(e) => setAnswerType(e.target.value)}
              className="px-3 py-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="single">Đơn</option>
              <option value="multiple">Đa</option>
            </select>
          </div>

          <div className="mb-4">
            <label className="block mb-2 font-medium text-black">Lựa chọn</label>
            {options &&
              options.map((option, index) => (
                <div key={index} className="flex mb-2 items-center">
                  <input
                    type={answerType === 'single' ? 'radio' : 'checkbox'} // Sử dụng radio hoặc checkbox tùy thuộc vào loại đáp án
                    checked={correctOptions.includes(index)}
                    onChange={() => handleToggleCorrectOption(index)}
                    className="mr-2"
                  />
                  <input
                    ref={(el) => (optionInputRefs.current[index] = el)}
                    type="text"
                    value={option}
                    onChange={(e) => handleOptionChange(index, e.target.value)}
                    onKeyDown={handleKeyDown}
                    className="flex-grow px-3 py-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    required
                  />
                  <button
                    type="button"
                    onClick={() => handleRemoveOption(index)}
                    className="px-3 py-2 ml-1 text-sm text-black bg-transparent border border-red-500 rounded-md hover:bg-red-500 hover:text-white"
                  >
                    Xóa
                  </button>
                </div>
              ))}
            <button
              type="button"
              onClick={handleAddOption}
              className="px-3 py-2 text-sm text-black bg-transparent border border-blue-500 rounded-md hover:bg-blue-500 hover:text-white"
            >
              Thêm đáp án
            </button>
          </div>
          {correctOptions && correctOptions.length > 0 && (
            <div className="mt-4">
              <p className="mb-2 font-medium text-black">Câu trả lời đúng:</p>
              <ul>
                {correctOptions.map((index) => (
                  <li key={index}>{options[index]}</li>
                ))}
              </ul>
            </div>
          )}

          <div className="flex">
            <button
              type="submit"
              className="max-w-fit px-4 py-2 m-4 text-sm font-medium text-white bg-green-500 rounded-md hover:bg-green-600"
            >
              Tạo câu hỏi
            </button>
            <Button
              onClick={onClose}
              className="max-w-fit px-3 py-2 m-4 text-sm font-medium text-white bg-red-500 rounded-md hover:bg-red-600"
            >
              Thoát
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
