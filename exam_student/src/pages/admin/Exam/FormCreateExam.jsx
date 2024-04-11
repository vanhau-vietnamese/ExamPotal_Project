import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { Button, FormInput, FormSelect } from '~/components';
import PropTypes from 'prop-types';
import { Question } from '~/layouts/components';
import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { createExam } from '~/apis';
import Icons from '~/assets/icons';
import { useExamStore } from '~/store';

const FormCreateExam = ({ onClose, cate }) => {
  const { addNewExam } = useExamStore((state) => state);
  const {
    control,
    formState: { errors },
    handleSubmit,
  } = useForm();
  const [showQuestionList, setShowQuestionList] = useState(false);
  const [selectedQuestions, setSelectedQuestions] = useState([]);

  const handleQuestionSelect = (questionID) => {
    if (selectedQuestions && selectedQuestions.includes(questionID)) {
      setSelectedQuestions(selectedQuestions.filter((id) => id !== questionID));
    } else {
      setSelectedQuestions([...selectedQuestions, questionID]);
    }
  };

  useEffect(() => {
    console.log(selectedQuestions);
  }, [selectedQuestions]);

  const handleChooseFromBank = () => {
    setShowQuestionList(true);
  };

  const handleFormSubmit = async (data) => {
    try {
      const body = {
        title: data.examName,
        categoryId: data.category,
        description: null,
        maxMarks: 100,
        durationMinutes: null,
        listQuestion: selectedQuestions.map((index) => ({
          questionId: index,
          marksOfQuestion: 1,
        })),
      };

      const response = await createExam(body);
      console.log('RESPONE', response);

      if (response) {
        addNewExam(response);
        toast.success('Tạo mới bài tập thành công', { toastId: 'create_exam' });
        onClose();
      }
    } catch (error) {
      toast.error(error.message, { toastId: 'create_exam' });
    }
  };

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="container mx-auto p-4 bg-slate-100 rounded-md w-[1000px]">
        <h3 className="mb-5">Tạo bài tập</h3>
        <form onSubmit={handleSubmit(handleFormSubmit)} className="w-full">
          <div className="flex flex-wrap -mx-3 mb-4">
            <div className="w-full flex px-3">
              <div className="m-3 w-[50%]">
                <FormInput
                  control={control}
                  name="examName"
                  title="Tên bài tập"
                  placeholder="Nhập tên bài tập"
                  required
                />
              </div>

              <div className="m-3 w-[50%]">
                <FormSelect
                  control={control}
                  name="category"
                  label="Danh mục"
                  placeholder="Chọn danh mục..."
                  required
                  error={errors.category?.message}
                  options={cate}
                />
              </div>
            </div>

            <div>
              <Button
                type="button"
                onClick={handleChooseFromBank}
                className="border border-gray-500 p-2 ml-3 flex text-sm"
              >
                Chọn câu hỏi <Icons.DownArrow />
              </Button>
            </div>
          </div>

          {showQuestionList && (
            <div className="mb-4">
              <div className="flex text-sm">
                <span className="text-sm font-semibold mb-2 mr-5">Danh sách câu hỏi</span>
                <span className="text-sm font-semibold mb-2 mr-5">Số câu đã chọn: </span>
              </div>
              <div className="bg-gray-400 w-full rounded-md">
                <div className="max-h-[500px] overflow-y-auto">
                  <Question
                    selectQues={selectedQuestions}
                    onQuestionSelect={handleQuestionSelect}
                  />
                </div>
              </div>
            </div>
          )}

          <div className="flex justify-center">
            <Button
              type="submit"
              className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 w-32 h-12 text-sm rounded m-1"
            >
              Tạo bài tập
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
  );
};

export default FormCreateExam;

FormCreateExam.propTypes = {
  onClose: PropTypes.func,
  cate: PropTypes.array.isRequired,
};
