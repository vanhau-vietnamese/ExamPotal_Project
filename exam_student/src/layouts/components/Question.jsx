import PropTypes from 'prop-types';
import { useQuestionStore } from '~/store';

export default function Question({ onQuestionSelect }) {
  const { questionList } = useQuestionStore((state) => state);
  console.log('QUES', questionList);
  console.log('BBBBB', questionList.answers);
  const handleSelect = (questionID) => {
    onQuestionSelect(questionID);
  };
  return (
    <div className="p-1">
      <div>Nội dung</div>
      {questionList.map((item) => (
        <div
          onClick={() => handleSelect(item.id)}
          key={item.id}
          className="border border-gray bg-white m-1 rounded-md shadow-md w-80%"
        >
          <h2 className="text-sm font-semibold ml-5 mt-5">Nội dung câu hỏi</h2>
          <ul className="ml-4 space-y-2">
            <li>{/* <AnswerList answers={questionList.answers} /> */}</li>
          </ul>
        </div>
      ))}
    </div>
  );
}
Question.propTypes = {
  onQuestionSelect: PropTypes.func.isRequired,
};
