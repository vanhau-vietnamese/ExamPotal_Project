import { Link } from 'react-router-dom';
import { router } from '~/routes';
import { Backdrop, Button } from '~/components';
import { useState } from 'react';
import FormCreateExam from './FormCreateExam';
import Icons from '~/assets/icons';
import { TestData } from '~/TestData';
import CreateLanguages from './CreateLanguages';

export default function ExamList() {
  const [isCreatingExam, setIsCreatingExam] = useState(false);

  const handleCreateExam = () => {
    setIsCreatingExam(true);
  };

  return (
    <div className="relative overflow-x-auto sm:rounded-lg w-full">
      <div className="flex justify-end mb-5">
        <div className="flex items-center space-x-2">
          <div className="flex items-center">
            <Icons.Search />
            <input
              type="text"
              id="search"
              className="block pt-2 ps-1 ml-2 text-sm text-gray-500 border border-gray-300 rounded-lg w-80 h-[40px] bg-gray-50 "
              placeholder=" Tìm kiếm..."
            />
          </div>
        </div>
        <Button
          className="w-[120px] ml-10 px-5 py-2 text-sm text-white bg-primary shadow-success hover:shadow-success_hover"
          onClick={handleCreateExam}
        >
          Tạo bài tập
        </Button>
      </div>
      <div className="pb-4 bg-white rounded-md">
        <div className="relative m-2 p-2 flex justify-between">
          <div className=" z-10">
            <CreateLanguages />
          </div>
        </div>
        <div className="mt-5 relative sm:rounded bg-white shadow-card w-full max-h-full overflow-hidden">
          <table className="block w-full text-sm text-left rtl:text-right border-collapse">
            <thead className="text-[#3b3e66] uppercase text-xs block w-full">
              <tr className="bg-[#d1d2de] rounded-se w-full flex items-center">
                <th className="p-3 w-[5%] min-w-[70px]">Mã số</th>
                <th className="p-3 w-[400px]">Tiêu đề</th>
                <th className="p-3 flex-shrink-0 w-[130px]">Số câu hỏi</th>
                <th className="p-3 flex-shrink-0 w-[190px]">Ngày tạo</th>
                <th className="p-3 flex-shrink-0 w-[100px]" align="center">
                  Hành động
                </th>
              </tr>
            </thead>
            <tbody className="overflow-y-scroll">
              {TestData.map((item) => (
                <tr
                  key={item.id}
                  className="flex items-center border-b border-[#d1d2de] transition-all hover:bg-[#d1d2de] hover:bg-opacity-30 max-h-fit font-semibold text-[#3b3e66]"
                >
                  <td className="p-3 w-[5%] min-w-[50px]">{item.id}</td>
                  <td className="p-3 w-[450px] break-words">{item.tittel}</td>
                  <td className="p-3 flex-shrink-0 w-[100px] break-words">{item.number}</td>
                  <td className="p-3 flex-shrink-0 w-[200px] break-words">{item.date}</td>
                  <td className="p-3 flex flex-shrink-0 w-[200px]">
                    <Link
                      to={router.detailExam}
                      className="font-medium text-yellow-600 dark:text-yellow-500 hover:underline"
                    >
                      <Icons.Eye />
                    </Link>
                    <Link className="ml-5 font-medium text-red-600 dark:text-red-500 hover:underline">
                      <Icons.Trash />
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {isCreatingExam && (
        <Backdrop opacity={0.35} className="overflow-auto">
          <FormCreateExam onClose={() => setIsCreatingExam(false)} />
        </Backdrop>
      )}
    </div>
  );
}
