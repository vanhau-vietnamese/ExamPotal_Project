import { compile } from 'html-to-text';
import moment from 'moment';
import Icons from '~/assets/icons';
import { Button } from '~/components';
import { useQuestionStore } from '~/store';

const compiledConvert = compile({
  limits: {
    ellipsis: ' ...',
  },
});

function QuestionTable() {
  const { questionList, openModal, setTargetQuestion } = useQuestionStore((state) => state);

  const handleOpenModal = ({ type, question }) => {
    setTargetQuestion(question);
    openModal(type);
  };

  return (
    <div className="mt-5 relative sm:rounded bg-white shadow-card w-full max-h-full overflow-hidden">
      <table className="block w-full text-sm text-left rtl:text-right border-collapse">
        <thead className="text-[#3b3e66] uppercase text-xs block w-full">
          <tr className="bg-[#d1d2de] rounded-se w-full flex items-center">
            <th className="p-3 w-[6%] flex-shrink-0">Mã số</th>
            <th className="p-3 flex-auto">Nội dung câu hỏi</th>
            <th className="p-3 flex-shrink-0 w-[20%]">Loại câu hỏi</th>
            <th className="p-3 flex-shrink-0 w-[20%]">Danh mục</th>
            <th className="p-3 flex-shrink-0 w-[13%]">Thời gian tạo</th>
            <th className="p-3 flex-shrink-0 w-[12%]" align="center">
              Hành động
            </th>
          </tr>
        </thead>
        <tbody className="overflow-y-auto block h-[calc(100vh-13rem)] w-full">
          {questionList && questionList.length > 0 ? (
            questionList.map((question) => (
              <tr
                key={question.id}
                className="flex items-center border-b border-[#d1d2de] transition-all hover:bg-[#d1d2de] hover:bg-opacity-30 h-[45px] font-semibold text-[#3b3e66]"
              >
                <td className="p-3 w-[6%] flex-shrink-0">{question.id}</td>
                <td className="p-3 flex-auto text-nowrap text-ellipsis overflow-hidden">
                  {compiledConvert(question.content)}
                </td>
                <td className="p-3 flex-shrink-0 w-[20%]">{question.questionType?.displayName}</td>
                <td className="p-3 flex-shrink-0 w-[20%]">{question.category?.title || '--'}</td>
                <td className="p-3 overflow-hidden flex-shrink-0 w-[13%]" align="left">
                  {moment(question.createdAt).format('HH:mm, DD/MM/YYYY')}
                </td>
                <td className="p-3 flex-shrink-0 w-[12%]">
                  <div className="flex items-center justify-center gap-x-2">
                    <Button
                      onClick={() => handleOpenModal({ type: 'view', question })}
                      className="text-xs rounded px-2 py-1 text-orange-500 hover:bg-orange-200 hover:bg-opacity-40"
                    >
                      <Icons.Eye />
                    </Button>
                    <Button
                      onClick={() => handleOpenModal({ type: 'edit', question })}
                      className="text-xs rounded px-2 py-1 text-blue-500 hover:bg-blue-200 hover:bg-opacity-40"
                    >
                      <Icons.Pencil />
                    </Button>
                    <Button
                      onClick={() => handleOpenModal({ type: 'delete', question })}
                      className="text-xs rounded px-2 py-1 text-danger hover:bg-red-200 hover:bg-opacity-40"
                    >
                      <Icons.Trash />
                    </Button>
                  </div>
                </td>
              </tr>
            ))
          ) : (
            <tr className="block w-full h-full">
              <td className="flex flex-col items-center justify-center gap-y-5 w-full h-full p-5 text-slate-400 font-semibold text-xl">
                <Icons.Empty />
                <span>Không có dữ liệu</span>
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default QuestionTable;
