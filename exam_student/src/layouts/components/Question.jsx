import { DatatestQuestion } from '~/DatatestQuestion';
import PropTypes from 'prop-types';
import AnswerList from './AnswerList';

export default function Question({ onQuestionSelect }) {
  //
  const handleSelect = (questionID) => {
    onQuestionSelect(questionID);
  };
  return (
    <div className="p-1">
      {DatatestQuestion.map((item) => (
        <div
          onClick={() => handleSelect(item.id)}
          key={item.id}
          className="border border-gray bg-white m-1 rounded-md shadow-md w-80%"
        >
          <h2 className="text-sm font-semibold ml-5 mt-5">{item.question}</h2>
          <ul className="ml-4 space-y-2">
            <li>
              <AnswerList answers={item.content} />
              <label className="flex items-center mb-5">
                <div className="flex">
                  <span className="font-bold text-green-700">{item.isCorrect}</span>
                </div>
              </label>
            </li>
          </ul>
        </div>
      ))}
    </div>
  );
}
Question.propTypes = {
  onQuestionSelect: PropTypes.func.isRequired,
};
