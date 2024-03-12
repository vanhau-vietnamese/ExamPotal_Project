import { TestData } from '~/TestData';

function QuestionItem() {
  return (
    <div>
      {TestData.map((data) => (
        <div key={data.id} className="p-5">
          <table className="block">
            <tbody>
              <tr className="block">
                <div className="w-3/4">{data.question}</div>
                <td className="w-1/4">
                  {data.content.map((option, index) => (
                    <span key={index} className="m-5">
                      {option}
                    </span>
                  ))}
                </td>
                <div className="w-1/4">{data.isCorrect}</div>
              </tr>

              <tr>
                <td colSpan="3">
                  <button className="bg-yellow-500 py-2 px-4 hover:bg-yellow-700 text-white rounded-md max-w-fit h-10 m-5">
                    Chỉnh sửa
                  </button>
                  <button className="bg-red-500 py-2 px-4 hover:bg-red-700 text-white rounded-md w-20 h-10 m-5">
                    Xóa
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
}

export default QuestionItem;
