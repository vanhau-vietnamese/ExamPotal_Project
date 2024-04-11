import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { Button, FormInput } from '~/components';
import PropTypes from 'prop-types';
import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { getQuesOfQuiz, updateQuiz } from '~/apis';
import Icons from '~/assets/icons';
import { useExamStore } from '~/store';
import { zodResolver } from '@hookform/resolvers/zod';
import { FormExamCreateSchema } from '~/validations/exam';
import { Question } from '~/layouts/components';

const FormEditExam = () => {
  const [showQuestionList, setShowQuestionList] = useState(false);
  const [selectedQuestions, setSelectedQuestions] = useState([]);
  const { targetExam, setTargetExam, openModal, updateExam } = useExamStore((state) => state);

  useEffect(() => {
    (async () => {
      try {
        const response = await getQuesOfQuiz(targetExam.id);
        if (response) {
          setSelectedQuestions(response.map((q) => q.id));
        }
      } catch (error) {
        toast.error(error);
      }
    })();
  }, []);

  const { control, handleSubmit } = useForm({
    mode: 'onSubmit',
    resolver: zodResolver(FormExamCreateSchema),
    defaultValues: {
      examName: targetExam.title,
      category: targetExam.category.title,
      listQuestion: selectedQuestions.map((item) => ({
        questionId: item,
        marksOfQuestion: 1,
      })),
    },
  });

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

  const handleFormUpdate = async (data) => {
    try {
      const body = {
        title: data.examName,
        categoryId: targetExam.id,
        description: 'Mô tả',
        maxMarks: 100,
        durationMinutes: 90,
        listQuestion: selectedQuestions.map((index) => ({
          questionId: index,
          marksOfQuestion: 1,
        })),
      };

      const response = await updateQuiz(targetExam.id, body);

      if (response) {
        updateExam(response);
        toast.success('Cập nhật bài tập thành công', { toastId: 'update_exam' });
        onClose();
      }
    } catch (error) {
      toast.error(error.message, { toastId: 'update_exam' });
    }
  };

  const onClose = () => {
    setTargetExam(null);
    openModal(null);
  };

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="container mx-auto p-4 bg-slate-100 rounded-md w-[1000px]">
        <h3 className="mb-5">Chỉnh sửa bài tập</h3>
        <form onSubmit={handleSubmit(handleFormUpdate)} className="w-full">
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

              <div className="m-2 w-[50%]">
                <span className="text-sm font-bold text-icon">Danh mục</span>
                <FormInput control={control} name="category" label="Danh mục" disabled />
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
              className="px-6 py-2 text-sm text-white bg-primary shadow-success hover:shadow-success_hover"
            >
              Cập nhật
            </Button>
            <Button
              type="button"
              onClick={onClose}
              className="px-6 py-2 ml-3 text-sm !border border-solid !border-danger text-danger hover:bg-danger hover:bg-opacity-10"
            >
              Thoát
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FormEditExam;

FormEditExam.propTypes = {
  onClose: PropTypes.func,
  cate: PropTypes.array.isRequired,
};
