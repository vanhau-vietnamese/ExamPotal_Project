import { Fragment, useEffect } from 'react';
import { toast } from 'react-toastify';
import { getQuestionTypes, getQuestions } from '~/apis';
import { Backdrop } from '~/components';
import { useQuestionStore } from '~/store';
import {
  CreateQuestionModal,
  FormEditQuestion,
  QuestionTable,
  ViewDetailQuestion,
} from './components';

function QuestionWrapper() {
  const { setQuestionList, isEditing, targetQuestion, setQuestionType } = useQuestionStore(
    (state) => state
  );

  useEffect(() => {
    (async () => {
      try {
        const listQuestion = await getQuestions();
        const questionTypes = await getQuestionTypes();
        setQuestionList(listQuestion);

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
  }, [setQuestionList, setQuestionType]);

  return (
    <Fragment>
      <div className="w-full">
        <div className="flex items-center w-full justify-between">
          <div />
          <CreateQuestionModal />
        </div>
        <div className="mt-4 w-full h-[calc(100%-4rem)]">
          <QuestionTable />
        </div>
      </div>

      {targetQuestion && (
        <Backdrop opacity={0.25}>
          {' '}
          {isEditing ? <FormEditQuestion /> : <ViewDetailQuestion />}
        </Backdrop>
      )}
    </Fragment>
  );
}

export default QuestionWrapper;
