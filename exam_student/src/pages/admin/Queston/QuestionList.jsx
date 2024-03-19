import Search from '~/assets/icons/Search';
import QuestionItem from './QuestionItem';
import { Backdrop, Button } from '~/components';
import { useState } from 'react';
import FormCreateQuestion from './FormCreateQuestion';
import { CreateLanguages } from '../Exam';

export default function ExamList() {
  const [isCreatingExam, setIsCreatingExam] = useState(false);

  const handleCreateExam = () => {
    setIsCreatingExam(true);
  };
  return (
    <div className="relative overflow-x-auto shadow-md sm:rounded-lg w-full">
      <div className="pb-4 bg-white rounded-md">
        <div className="relative m-2 p-2 flex justify-between">
          <div>
            <CreateLanguages />
          </div>
          <div className="flex items-center space-x-2">
            <div className="flex items-center">
              <Search />
              <input
                type="text"
                id="table-search"
                className="block pt-2 ps-1 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500"
                placeholder="Tìm kiếm..."
              />
            </div>
            <Button
              className="p-1 text-sm h-10 rounded-xl justify-center font-bold bg-green-500 hover:bg-green-700 text-white"
              onClick={handleCreateExam}
            >
              Tạo câu hỏi
            </Button>
          </div>
        </div>
        <div className="max-h-[500px] overflow-y-auto">
          <table>
            <thead></thead>
            <tbody>
              <tr>
                <th>
                  <QuestionItem></QuestionItem>
                </th>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      {isCreatingExam && (
        <Backdrop opacity={0.35} className="overflow-auto">
          <FormCreateQuestion onClose={() => setIsCreatingExam(false)}></FormCreateQuestion>
        </Backdrop>
      )}
    </div>
  );
}
