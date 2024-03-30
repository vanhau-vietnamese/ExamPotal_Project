import { zodResolver } from '@hookform/resolvers/zod';
import PropTypes from 'prop-types';
import { useForm } from 'react-hook-form';
import { Button, FormSelect } from '~/components';
import FormEditor from '~/components/Form/FormEditor';
import { FormQuestionCreateSchema } from '~/validations';
import AnswersCreate from './AnswersCreate';
import { useEffect } from 'react';
import { useState } from 'react';
import { getAllCategories, getQuestionTypes } from '~/apis';
import { toast } from 'react-toastify';

export default function FormQuestionCreate({ onClose }) {
  const {
    control,
    formState: { errors },
    watch,
    handleSubmit,
  } = useForm({
    mode: 'onSubmit',
    resolver: zodResolver(FormQuestionCreateSchema),
  });

  const [categories, setCategories] = useState([]);
  const [questionType, setQuestionType] = useState([]);

  const selectedQuestionType = watch('questionType');

  useEffect(() => {
    (async () => {
      try {
        const listCategories = await getAllCategories();
        const questionTypes = await getQuestionTypes();

        if (listCategories && listCategories.length > 0) {
          setCategories(
            listCategories.map((category) => ({
              display: category.title,
              value: category.id,
            }))
          );
        }

        if (questionTypes && questionTypes.length > 0) {
          setQuestionType(
            questionTypes.map((type) => ({
              display: type.displayName,
              value: type.alias,
            }))
          );
        }
      } catch (error) {
        toast.error(error.message, { toastId: 'fetch_question' });
      }
    })();
  }, []);

  const handleCreateQuestion = async (data) => {
    console.log(data);
  };

  return (
    <div className="w-full h-full mx-auto max-w-5xl p-10">
      <form
        className="w-full h-full bg-white rounded-lg flex flex-col justify-between"
        onSubmit={handleSubmit(handleCreateQuestion)}
      >
        <div className="text-gray-700 p-4 border-b border-dashed border-strike">
          <h3>Tạo mới câu hỏi</h3>
        </div>
        <div className="flex-1 max-h-[700px] overflow-y-auto p-4">
          <div className="flex items-center justify-between w-full gap-x-5 mb-5">
            <FormSelect
              control={control}
              name="questionType"
              label="Loại câu hỏi"
              placeholder="Chọn loại câu hỏi..."
              error={errors.questionType?.message}
              required
              options={questionType}
            />
            <FormSelect
              control={control}
              name="category"
              label="Danh mục"
              placeholder="Chọn danh mục..."
              required
              error={errors.category?.message}
              options={categories}
            />
          </div>
          <FormEditor
            control={control}
            name="content"
            title="Nội dung câu hỏi"
            placeholder="Nhập nội dung câu hỏi..."
            required
            error={errors.content?.message}
          />
          <AnswersCreate
            control={control}
            name="answers"
            label="Đáp án câu hỏi"
            type={selectedQuestionType}
          />
        </div>
        <div className="flex items-center justify-end px-4 py-3 gap-x-5 border-t border-dashed border-strike">
          <Button
            type="button"
            className="px-6 py-2 text-sm !border !border-danger text-danger hover:bg-danger hover:bg-opacity-5"
            onClick={onClose}
          >
            Hủy bỏ
          </Button>
          <Button
            type="submit"
            className="px-6 py-2 text-sm text-white bg-primary shadow-success hover:shadow-success_hover"
          >
            Tạo mới
          </Button>
        </div>
      </form>
    </div>
  );
}

FormQuestionCreate.propTypes = {
  onClose: PropTypes.func.isRequired,
};
