import { useState } from 'react';
import { Button } from '~/components';

import PropTypes from 'prop-types';

FormCreateExam.propTypes = {
  onClose: PropTypes.func,
};

function FormCreateExam({ onClose }) {
  const [creator, setCreator] = useState('');
  const [date, setDate] = useState('');
  const [language, setLanguage] = useState('');
  const [questionCount, setQuestionCount] = useState('');
  const [deadline, setDeadline] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Xử lý logic khi submit form
  };

  return (
    <div className="flex items-center justify-center pt-6">
      <div className="container mx-auto p-4 bg-slate-100 rounded-md max-w-[800px]">
        <h2 className="text-2xl font-bold mb-4">Tạo bài tập</h2>

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="creator" className="block text-gray-700 text-sm font-bold mb-2">
              Người tạo
            </label>
            <input
              id="creator"
              type="text"
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              placeholder="Nhập người tạo"
              value={creator}
              onChange={(e) => setCreator(e.target.value)}
            />
          </div>

          <div className="mb-4">
            <label htmlFor="date" className="block text-gray-700 text-sm font-bold mb-2">
              Ngày tạo
            </label>
            <input
              id="date"
              type="date"
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              value={date}
              onChange={(e) => setDate(e.target.value)}
            />
          </div>

          <div className="mb-4">
            <label htmlFor="language" className="block text-gray-700 text-sm font-bold mb-2">
              Ngôn ngữ
            </label>
            <select
              id="language"
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              value={language}
              onChange={(e) => setLanguage(e.target.value)}
            >
              <option value="">Chọn ngôn ngữ</option>
              <option value="javascript">JavaScript</option>
              <option value="python">Python</option>
              <option value="java">Java</option>
              {/* Thêm các lựa chọn ngôn ngữ khác vào đây */}
            </select>
          </div>

          <div className="mb-4">
            <label htmlFor="questionCount" className="block text-gray-700 text-sm font-bold mb-2">
              Số lượng câu hỏi
            </label>
            <input
              id="questionCount"
              type="number"
              min="1"
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              placeholder="Nhập số lượng câu hỏi"
              value={questionCount}
              onChange={(e) => setQuestionCount(e.target.value)}
            />
          </div>

          <div className="mb-4">
            <label htmlFor="deadline" className="block text-gray-700 text-sm font-bold mb-2">
              Hạn nộp bài
            </label>
            <input
              id="deadline"
              type="datetime-local"
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              value={deadline}
              onChange={(e) => setDeadline(e.target.value)}
            />
          </div>

          <div className="flex justify-end">
            <button
              type="submit"
              className="bg-green-500 hover:bg-green-700 max-w-fit px-3 py-2 m-4 text-sm font-medium text-white rounded-md"
            >
              Tạo bài tập
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

export default FormCreateExam;
