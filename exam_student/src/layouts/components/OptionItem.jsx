import PropTypes from 'prop-types';

export default function OptionItem({ options }) {
  const correctAnswers = options.filter((answer) => answer.correct === true);

  return (
    <div className="mt-5">
      {options.map((answer, index) => (
        <div key={index}>
          <p>
            Đáp án {index + 1}: {answer.content}
          </p>
        </div>
      ))}
      <label className="flex items-center mb-5">
        <div className="flex">
          {correctAnswers.length > 0 && (
            <div>
              <p className="mt-5 font-bold text-green-700 ">Đáp án đúng:</p>
              {correctAnswers.map((correctAnswer, index) => (
                <p key={index}>{correctAnswer.content}</p>
              ))}
            </div>
          )}
        </div>
      </label>
    </div>
  );
}

OptionItem.propTypes = {
  options: PropTypes.array.isRequired,
};
