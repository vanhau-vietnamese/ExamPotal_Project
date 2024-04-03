import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { getQuestions } from '~/apis';
import { useQuestionStore } from '~/store';
import { CreateQuestionModal, EditQuestion, QuestionTable, ViewDetailQuestion } from './components';
import { Fragment } from 'react';
import { Backdrop } from '~/components';

function QuestionWrapper() {
  const { setQuestionList, isEditing, targetQuestion } = useQuestionStore((state) => state);

  useEffect(() => {
    (async () => {
      try {
        const listQuestion = await getQuestions();
        setQuestionList(listQuestion);
      } catch (error) {
        toast.error(error.message, { toastId: 'fetch_question' });
      }
    })();
  }, [setQuestionList]);

  return (
    <Fragment>
      <div className="w-full px-4">
        <div className="flex items-center w-full justify-between">
          <div />
          <CreateQuestionModal />
        </div>
        <div className="mt-4 w-full h-[calc(100%-4rem)]">
          <QuestionTable />
        </div>
      </div>
      {/* {!isEditing && !targetQuestion && <EditQuestion />}
      {isEditing && !targetQuestion && <ViewDetailQuestion />} */}
      {targetQuestion && (
        <Backdrop opacity={0.25}> {isEditing ? <EditQuestion /> : <ViewDetailQuestion />}</Backdrop>
      )}
    </Fragment>
  );
}

export default QuestionWrapper;
