import PropTypes from 'prop-types';
import { useQuestionStore } from '~/store';
import { useEffect } from 'react';
import { toast } from 'react-toastify';
import { getQuestions } from '~/apis';
import { compile } from 'html-to-text';
import Icons from '~/assets/icons';

export default function Question({ onQuestionSelect, selectQues }) {
  const { questionList, setQuestionList } = useQuestionStore((state) => state);

  const compiledConvert = compile({
    limits: {
      ellipsis: ' ...',
    },
  });

  useEffect(() => {
    (async () => {
      try {
        const listQuestion = await getQuestions();
        setQuestionList(listQuestion);
      } catch (error) {
        toast.error(error.message, { toastId: 'fetch_question' });
      }
    })();
  }, [setQuestionList]);

  const handleSelect = (questionID) => {
    onQuestionSelect(questionID);
  };

  return (
    <div className="w-full">
      <table className="block w-full text-sm text-left rtl:text-right border-collapse">
        <thead className="text-[#3b3e66] uppercase text-xs block w-full">
          <tr className="bg-[#d1d2de] rounded-se w-full flex items-center">
            <th className="p-3 flex-auto w-[70%]">Nội dung câu hỏi</th>
            <th className="p-3 flex-auto w-[20%]">Danh mục</th>
            <th className="p-3 flex-auto w-[10%]"></th>
          </tr>
        </thead>
        <tbody className="overflow-y-auto block w-full">
          {questionList.map((item, index) => (
            <tr
              onClick={() => handleSelect(item.id)}
              key={item.id}
              className="flex bg-slate-50 hover:bg-slate-100 items-center border-b border-[#d1d2de] transition-all hover:bg-[#d1d2de] hover:bg-opacity-30 h-[45px] font-semibold text-[#3b3e66]"
            >
              <td className="p-3 flex flex-auto w-[80%]">
                {index + 1}.{compiledConvert(item.content)}
              </td>
              <td className="p-3 flex-shrink-0 w-[20%]">{item.category?.title || '--'}</td>
              <td className="p-3 flex-shrink-0 w-[10%]">
                {selectQues.includes(item.id) && (
                  <div className="text-white bg-primary rounded-full w-5 h-5 flex items-center justify-center flex-shrink-0">
                    <Icons.Check />
                  </div>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
Question.propTypes = {
  onQuestionSelect: PropTypes.func.isRequired,
  selectQues: PropTypes.array.isRequired,
};
