import { zodResolver } from '@hookform/resolvers/zod';
import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { useFieldArray, useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { getAllCategories } from '~/apis';
import Icons from '~/assets/icons';
import { Button, FormInput, FormSelect } from '~/components';
import FormEditor from '~/components/Form/FormEditor';
import { FormQuestionCreateSchema } from '~/validations';
import AnswersCreate from './AnswersCreate';
import { useQuestionStore } from '~/store';

export default function EditQuestion() {
  const { setIsEditing, setTargetQuestion, targetQuestion } = useQuestionStore((state) => state);
  console.log('TAGET', targetQuestion);
  const {
    control,
    formState: { errors },
    watch,
    handleSubmit,
  } = useForm({
    mode: 'onSubmit',
    resolver: zodResolver(FormQuestionCreateSchema),
    defaultValues: {
      questionType: targetQuestion.questionType.displayName,
      category: targetQuestion.category.id,
      content: targetQuestion.content,
      answers: targetQuestion.answers.map((item) => ({
        content: item.content,
        isCorrect: Boolean(item.correct),
      })),
    },
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'answers',
  });

  const [categories, setCategories] = useState([]);
  const selectedQuestionType = watch('questionType');

  useEffect(() => {
    (async () => {
      try {
        const listCategories = await getAllCategories();

        if (listCategories && listCategories.length > 0) {
          setCategories(
            listCategories.map((category) => ({
              display: category.title,
              value: category.id,
            }))
          );
        }
      } catch (error) {
        toast.error(error.message, { toastId: 'fetch_question' });
      }
    })();
  }, []);

  const handleEditQuestion = async (data) => {
    console.log('DATA', data);
    // try {
    //   const body = {
    //     content: data.content,
    //     categoryId: data.category,
    //     questionTypeId: data.questionType,
    //     answerRequestList: data.answers.map((answer) => ({
    //       media: answer.media || null,
    //       content: answer.content,
    //       correct: answer.isCorrect,
    //     })),
    //   };
    // } catch (error) {
    //   toast.error(error.message, { toastId: 'create_question' });
    // }
  };

  return (
    <div className="w-full h-full mx-auto max-w-5xl p-10">
      <form
        className="w-full h-full bg-white rounded-lg flex flex-col justify-between"
        onSubmit={handleSubmit(handleEditQuestion)}
      >
        <div className="text-gray-700 p-4 border-b border-dashed border-strike">
          <h3>Tạo mới câu hỏi</h3>
        </div>
        <div className="flex-1 max-h-[700px] overflow-y-auto p-4">
          <div className="flex items-center justify-between w-full gap-x-5 mb-5">
            <FormInput
              control={control}
              name="questionType"
              title="Loại câu hỏi"
              required
              disabled
            />
            <FormSelect
              control={control}
              name="category"
              label="Danh mục"
              required
              error={errors.category?.message}
              options={categories}
              className="mb-5"
            />
          </div>
          <FormEditor
            control={control}
            name="content"
            title="Nội dung câu hỏi"
            required
            error={errors.content?.message}
          />

          <div className="w-full mt-5">
            <div className="flex items-center w-full justify-between mb-2">
              <label className="block p-1 text-sm font-bold text-icon">
                {'Đáp án câu hỏi'}
                <strong className="text-error"> *</strong>
              </label>

              <Button
                type="button"
                className="p-1 text-sm text-primary flex items-center gap-1 hover:bg-primary hover:bg-opacity-10 disabled:hover:bg-transparent"
                onClick={() => append({ content: '', correct: false })}
                disable={!selectedQuestionType}
              >
                <Icons.Plus />
                <span>Thêm đáp án</span>
              </Button>
            </div>
            <div className="flex flex-col gap-4">
              {fields.map((_, index) => (
                <AnswersCreate
                  key={_.id}
                  control={control}
                  name={`answers.${index}`}
                  inputName="answers"
                  error={errors?.answers?.[index]}
                  type={selectedQuestionType}
                  onRemove={() => remove(index)}
                />
              ))}
            </div>
          </div>
        </div>
        <div className="flex items-center justify-end px-4 py-3 gap-x-5 border-t border-dashed border-strike">
          <Button
            type="button"
            className="px-6 py-2 text-sm !border !border-danger text-danger hover:bg-danger hover:bg-opacity-5"
            onClick={() => {
              setTargetQuestion(null);
              setIsEditing(false);
            }}
          >
            Hủy bỏ
          </Button>
          <Button
            type="submit"
            className="px-6 py-2 text-sm text-white bg-primary shadow-success hover:shadow-success_hover"
          >
            Lưu
          </Button>
        </div>
      </form>
    </div>
  );
}

EditQuestion.propTypes = {
  defaultValues: PropTypes.object,
};
