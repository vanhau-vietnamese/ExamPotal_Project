import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { getQuestions } from '~/apis';
import { useQuestionStore } from '~/store';
import { CreateQuestionModal, QuestionTable } from './components';

function QuestionWrapper() {
  const { setLoading, setQuestionList } = useQuestionStore((state) => state);

  useEffect(() => {
    (async () => {
      setLoading(true);
      try {
        const listQuestion = await getQuestions();
        setQuestionList(listQuestion);
      } catch (error) {
        toast.error(error.message, { toastId: 'fetch_question' });
      } finally {
        setLoading(false);
      }
    })();
  }, [setLoading, setQuestionList]);

  return (
    <div className="w-full px-4">
      <div className="flex items-center w-full justify-between">
        <div />
        <CreateQuestionModal />
      </div>
      <div className="mt-4 w-full h-[calc(100%-4rem)]">
        <QuestionTable />
      </div>
    </div>
  );
}

export default QuestionWrapper;
