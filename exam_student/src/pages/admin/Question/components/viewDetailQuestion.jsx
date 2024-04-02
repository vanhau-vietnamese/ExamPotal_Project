import { Button } from '~/components';
import PropTypes from 'prop-types';
import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { getQuestion } from '~/apis';
import { useState } from 'react';
import { OptionItem } from '~/layouts/components';

function ViewDetailQuestion({ onClose, isSelect }) {
  const [questions, setQuestions] = useState([]);

  const selectedQuestion = questions.find((item) => item.id === isSelect.id);

  useEffect(() => {
    (async () => {
      try {
        const listQuestions = await getQuestion();

        if (listQuestions && listQuestions.length > 0) {
          setQuestions(
            listQuestions.map((question) => ({
              id: question.id,
              content: question.content,
              questionType: question.questionType.displayName,
              answers: question.answers.map((index) => ({
                media: index.media || null,
                content: index.content,
                isCorrect: index.isCorrect,
              })),
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
        {selectedQuestion && (
          <div key={selectedQuestion.id} className="p-3">
            <h1 className="m-5">Chi tiết câu hỏi: {selectedQuestion.id} </h1>
            <h3 className="text-sm font-semibold ml-5 mt-5">{selectedQuestion.questionType}</h3>
            <div className="border border-gray bg-white m-1 rounded-md shadow-md w-80%">
              <h2 className="text-sm font-semibold ml-5 mt-5">{selectedQuestion.content}</h2>

              <ul className="ml-4 space-y-2">
                <OptionItem options={selectedQuestion.answers} />
              </ul>
            </div>
          </div>
        )}

        <Button
          className="px-6 py-2 text-sm !border !border-danger text-danger hover:bg-danger hover:bg-opacity-5"
          onClick={onClose}
        >
          Thoát
        </Button>
      </form>
    </div>
  );
}

export default ViewDetailQuestion;

ViewDetailQuestion.propTypes = {
  onClose: PropTypes.func.isRequired,
  isSelect: PropTypes.object.isRequired,
};
