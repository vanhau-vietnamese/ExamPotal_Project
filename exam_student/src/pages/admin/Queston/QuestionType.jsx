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
          setData(response.data);
        }
      })
      .catch((error) => {
        console.error('Error fetching data', error);
      });
  }, []);

  const handleSelectChange = (event) => {
    const selectAlias = event.target.value;
    const selectItem = data.length > 0 && data.find((i) => i.alias === selectAlias);
    onChange(selectItem);
  };

  return (
    <div>
      <select
        onChange={handleSelectChange}
        className="px-3 py-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
      >
        {data &&
          data.map((item) => (
            <option key={item.alias} value={item.alias}>
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
};
