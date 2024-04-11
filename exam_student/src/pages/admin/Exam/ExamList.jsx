import { Backdrop, Button } from '~/components';
import { useState } from 'react';
import FormCreateExam from './FormCreateExam';
import Icons from '~/assets/icons';
import moment from 'moment';
import CreateLanguages from './CreateLanguages';
import { useEffect } from 'react';
import { getAllCategories } from '~/apis';
import { toast } from 'react-toastify';
import { useExamStore } from '~/store';

export default function ExamList() {
  const { examList, setTargetExam, openModal } = useExamStore((state) => state);
  const [isCreatingExam, setIsCreatingExam] = useState(false);

  const handleCreateExam = () => {
    setIsCreatingExam(true);
  };

  const handleOpenModal = ({ type, exam }) => {
    setTargetExam(exam);
    openModal(type);
  };

  const [categories, setCategories] = useState([]);
  useEffect(() => {
    (async () => {
      try {
        const listCategories = await getAllCategories();

        if (listCategories && listCategories.length > 0) {
          setCategories(
            listCategories.map((category) => ({
              display: category.title,
              value: category.id,
            }))
          );
        }
      } catch (error) {
        toast.error(error.message, { toastId: 'fetch_question' });
      }
    })();
  }, []);

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
            <CreateLanguages cate={categories} />
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
              {examList.map((item, index) => (
                <tr
                  key={item.id}
                  className="flex items-center border-b border-[#d1d2de] transition-all hover:bg-[#d1d2de] hover:bg-opacity-30 max-h-fit font-semibold text-[#3b3e66]"
                >
                  <td className="p-3 w-[5%] min-w-[50px]">{index + 1}</td>
                  <td className="p-3 w-[450px] break-words">{item.title}</td>
                  <td className="p-3 flex-shrink-0 w-[100px] break-words">
                    {item.numberOfQuestions}
                  </td>
                  <td className="p-3 overflow-hidden flex-shrink-0 w-[13%]" align="left">
                    {moment(item.createdAt).format('HH:mm, DD/MM/YYYY')}
                  </td>
                  <td className="p-3 flex flex-shrink-0 w-[200px]">
                    <Button
                      onClick={() => handleOpenModal({ type: 'view', item })}
                      className="text-xs rounded px-2 py-1 text-yellow-500 hover:bg-yellow-200 hover:bg-opacity-40"
                    >
                      <Icons.Eye />
                    </Button>
                    <Button
                      onClick={() => handleOpenModal({ type: 'edit', item })}
                      className="text-xs rounded px-2 py-1 text-blue-500 hover:bg-blue-200 hover:bg-opacity-40"
                    >
                      <Icons.Pencil />
                    </Button>
                    <Button
                      onClick={() => handleOpenModal({ type: 'delete', item })}
                      className="text-xs rounded px-2 py-1 text-red-500 hover:bg-red-200 hover:bg-opacity-40"
                    >
                      <Icons.Trash />
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {isCreatingExam && (
        <Backdrop opacity={0.35} className="overflow-auto">
          <FormCreateExam cate={categories} onClose={() => setIsCreatingExam(false)} />
        </Backdrop>
      )}
    </div>
  );
}
