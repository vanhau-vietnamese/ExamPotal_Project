import moment from 'moment';
import Icons from '~/assets/icons';
import { Button, EditorViewer } from '~/components';
import { useQuestionStore } from '~/store';

function QuestionTable() {
  const { questionList } = useQuestionStore((state) => state);

  return (
    <div className="mt-5 relative sm:rounded bg-white shadow-card w-full max-h-full overflow-hidden">
      <table className="block w-full text-sm text-left rtl:text-right border-collapse">
        <thead className="text-[#3b3e66] uppercase text-xs block w-full">
          <tr className="bg-[#d1d2de] rounded-se w-full flex items-center">
            <th className="p-3 w-[10%]">Mã số</th>
            <th className="p-3 flex-auto">Nội dung câu hỏi</th>
            <th className="p-3 flex-shrink-0 min-w-[250px]">Loại câu hỏi</th>
            <th className="p-3 flex-shrink-0 min-w-[300px]">Danh mục</th>
            <th className="p-3 flex-shrink-0 min-w-[180px]">Ngày tạo</th>
            <th className="p-3 flex-shrink-0 min-w-[180px]" align="center">
              Hành động
            </th>
          </tr>
        </thead>
        <tbody className="overflow-y-auto block h-[calc(100vh-13rem)] w-full">
          {questionList &&
            questionList.map((question) => (
              <tr
                key={question.id}
                className="flex items-center border-b border-[#d1d2de] transition-all hover:bg-[#d1d2de] hover:bg-opacity-30 h-[45px] font-semibold text-[#3b3e66]"
              >
                <td className="p-3 w-[10%]">{question.id}</td>
                <td className="p-3 flex-auto text-nowrap text-ellipsis overflow-hidden">
                  <EditorViewer content={question.content} />
                </td>
                <td className="p-3 flex-shrink-0 w-[250px]">
                  {question.questionType?.displayName}
                </td>
                <td className="p-3 flex-shrink-0 w-[300px]">{question.category?.title || '--'}</td>
                <td className="p-3 overflow-hidden flex-shrink-0 w-[200px]">
                  {moment(question.createdAt).format('HH:mm, DD/MM/YYYY')}
                </td>
                <td className="p-3 flex-shrink-0">
                  <div className=" flex items-center justify-center gap-x-2">
                    <Button className="text-xs rounded px-2 py-1 text-orange-500 hover:bg-orange-200 hover:bg-opacity-40">
                      <Icons.Eye />
                    </Button>
                    <Button className="text-xs rounded px-2 py-1 text-blue-500 hover:bg-blue-200 hover:bg-opacity-40">
                      <Icons.Pencil />
                    </Button>
                    <Button className="text-xs rounded px-2 py-1 text-danger hover:bg-red-200 hover:bg-opacity-40">
                      <Icons.Trash />
                    </Button>
                  </div>
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}

export default QuestionTable;
