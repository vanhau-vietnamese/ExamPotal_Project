import { useEffect, useState } from 'react';
import { axiosClient } from '~/apis';
import PropTypes from 'prop-types';

const QuestionType = ({ onChange }) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axiosClient
      .get('/question-type/')
      .then((response) => {
        if (response) {
          setData(response);
        }
      })
      .catch((error) => {
        console.error('Error fetching data', error);
      });
  }, []);

  const handleSelectChange = (event) => {
    const selectedAlias = event.target.value;
    onChange(selectedAlias);
    const selectdisplayName = event.target.value;
    onChange(selectdisplayName);
  };

  return (
    <div>
      <select
        onChange={handleSelectChange}
        className="px-3 py-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
      >
        {data.map((item) => (
          // JSON.stringify: chuyen oject thanh chuoi
          <option key={item.alias} value={JSON.stringify(item)}>
            {item.displayName}
          </option>
        ))}
      </select>
    </div>
  );
};

export default QuestionType;

QuestionType.propTypes = {
  onChange: PropTypes.func.isRequired,
  answerType: PropTypes.string.isRequired,
};
