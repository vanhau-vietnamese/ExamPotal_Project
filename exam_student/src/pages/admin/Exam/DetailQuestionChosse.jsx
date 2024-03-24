import PropTypes from 'prop-types';

export default function DetailQuestionChosse({ questions }) {
  return (
    <div>
      <div>Câu đã chọn:</div>
      <ul>
        {questions.map((questionContent, index) => (
          <li key={index}>
            <h5>Question: {index + 1}</h5>
            <p>{questionContent.question}</p>
            <p>{questionContent.content}</p>
            <p>{questionContent.isCorrect}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}
DetailQuestionChosse.propTypes = {
  questions: PropTypes.array.isRequired,
};
