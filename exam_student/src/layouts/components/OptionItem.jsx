import PropTypes from 'prop-types';

export default function OptionItem({ options }) {
  const correctAnswers = options.find((answer) => answer.isCorrect === true);
  return (
    <div>
      {options.map((answers, index) => (
        <div key={index}>
          <p>
            Đáp án {index + 1}: {answers.content}
          </p>
        </div>
      ))}
      <label className="flex items-center mb-5">
        <div className="flex">
          {correctAnswers && (
            <p className=" mt-5 font-bold text-green-700">Đáp án: {correctAnswers.content}</p>
          )}
        </div>
      </label>
    </div>
  );
}

OptionItem.propTypes = {
  options: PropTypes.array.isRequired,
};
