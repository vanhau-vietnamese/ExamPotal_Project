import { zodResolver } from '@hookform/resolvers/zod';
import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { getAllCategories } from '~/apis';
import { Button, EditorViewer, FormSelect } from '~/components';
import { useQuestionStore } from '~/store';
import { FormQuestionCreateSchema } from '~/validations';
import AnswerList from '~/layouts/components/AnswerList';

export default function EditQuestion() {
  const { setIsEditing, setTargetQuestion, targetQuestion, questionTypes } = useQuestionStore(
    (state) => state
  );
  const {
    control,
    formState: { errors },
  } = useForm({
    mode: 'onSubmit',
    resolver: zodResolver(FormQuestionCreateSchema),
    defaultValues: {
      questionType: targetQuestion.questionType.alias,
      category: targetQuestion.category.id,
      content: targetQuestion.content,
      answers: targetQuestion.answers.map((item) => ({
        content: item.content,
        isCorrect: Boolean(item.correct),
      })),
    },
  });

  const [categories, setCategories] = useState([]);

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

  return (
    <div className="w-full h-full mx-auto max-w-5xl p-10">
      <form className="w-full h-full bg-white rounded-lg flex flex-col justify-between">
        <div className="text-gray-700 p-4 border-b border-dashed border-strike">
          <h3>Chỉnh sửa câu hỏi</h3>
        </div>
        <div className="flex-1 max-h-[700px] overflow-y-auto p-4">
          <div className="flex items-center justify-between w-full gap-x-5 mb-5">
            <FormSelect
              control={control}
              name="questionType"
              label="Loại câu hỏi"
              options={questionTypes}
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
              disabled
            />
          </div>
          <div>
            <label className="block p-1 text-sm font-bold text-icon">
              {'Nội dung câu hỏi'}
              <strong className="text-error"> *</strong>
            </label>
            <div className="border border-spacing-1 rounded-md p-5">
              <EditorViewer content={targetQuestion.content} />
            </div>
          </div>

          <div className="w-full mt-5">
            <div className="flex items-center w-full justify-between mb-2">
              <label className="block p-1 text-sm font-bold text-icon">
                {'Đáp án câu hỏi'}
                <strong className="text-error"> *</strong>
              </label>
            </div>
            <div>
              <AnswerList answers={targetQuestion.answers} />
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
        </div>
      </form>
    </div>
  );
}

EditQuestion.propTypes = {
  defaultValues: PropTypes.object,
};
