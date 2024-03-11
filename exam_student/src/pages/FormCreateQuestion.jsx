import { useState } from 'react';
import { Button, TextEditor } from '~/components';

import PropTypes from 'prop-types';

FormCreateQuestion.propTypes = {
  onClose: PropTypes.func,
};

export default function FormCreateQuestion({ onClose }) {
  const [question, setQuestion] = useState('');
  const [options, setOptions] = useState([]);
  const [correctOption, setCorrectOption] = useState('');

  const handleAddOption = () => {
    setOptions([...options, '']);
  };

  const handleOptionChange = (index, value) => {
    const updatedOptions = [...options];
    updatedOptions[index] = value;
    setOptions(updatedOptions);
  };

  const handleRemoveOption = (index) => {
    const updatedOptions = [...options];
    updatedOptions.splice(index, 1);
    setOptions(updatedOptions);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log({
      question,
      options,
      correctOption,
    });
  };

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="flex items-center justify-center bg-slate-50 w-full max-w-[800px] rounded-xl ">
        <form onSubmit={handleSubmit} className="max-w-[700px] mx-auto p-5 w-full">
          <div className="mb-4">
            <label htmlFor="question" className="block mb-2 font-medium text-black">
              Nhập tên ngôn ngữ
            </label>
            <input
              type="text"
              id="question"
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              className="w-full px-3 py-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              required
            />
          </div>

          <div className="mb-4">
            <label htmlFor="question" className="block mb-2 font-medium text-black">
              Nhập câu hỏi
            </label>
            <TextEditor></TextEditor>
            {/* <input
              type="text"
              id="question"
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              className="w-full px-3 py-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              required
            /> */}
          </div>

          <div className="mb-4">
            <label className="block mb-2 font-medium text-black">Lựa chọn</label>
            {options.map((option, index) => (
              <div key={index} className="flex mb-2">
                <input
                  type="text"
                  value={option}
                  onChange={(e) => handleOptionChange(index, e.target.value)}
                  className="flex-grow px-3 py-2 mr-2 border border-black rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                  required
                />
                <button
                  type="button"
                  onClick={() => handleRemoveOption(index)}
                  className="px-3 py-2 text-sm text-black bg-transparent border border-red-500 rounded-md hover:bg-red-500 hover:text-white"
                >
                  Xóa
                </button>
              </div>
            ))}
            <button
              type="button"
              onClick={handleAddOption}
              className="px-3 py-2 text-sm text-black bg-transparent border border-blue-500 rounded-md hover:bg-blue-500 hover:text-white"
            >
              Thêm đáp án
            </button>
          </div>

          <div className="mb-4">
            <label htmlFor="correctOption" className="block mb-2 font-medium text-black">
              Đáp án chính xác
            </label>
            <select
              id="correctOption"
              value={correctOption}
              onChange={(e) => setCorrectOption(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              required
            >
              <option value="" disabled>
                Chọn đáp án đúng
              </option>
              {options.map((option, index) => (
                <option key={index} value={option}>
                  {option}
                </option>
              ))}
            </select>
          </div>

          <div className="flex">
            <button
              type="submit"
              className="max-w-fit px-4 py-2 m-4 text-sm font-medium text-white bg-blue-500 rounded-md hover:bg-blue-600"
            >
              Tạo câu hỏi
            </button>
            <Button
              onClick={onClose}
              className="max-w-fit px-3 py-2 m-4 text-sm font-medium text-white bg-red-500 rounded-md hover:bg-red-600"
            >
              Thoát
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
