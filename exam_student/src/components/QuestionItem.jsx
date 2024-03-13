import { Link } from 'react-router-dom';
import { TestData } from '~/TestData';

function QuestionItem() {
  return (
    <div>
      {TestData.map((data) => (
        <div key={data.id} className="p-5">
          <table className="block">
            <div className="bg-slate-50 rounded-md shadow-md">
              <tbody>
                <tr className="block m-3">
                  <div className="w-3/4">{data.question}</div>
                  <td className="w-1/4">
                    {data.content.map((option, index) => (
                      <span key={index} className="m-3 block">
                        {option}
                      </span>
                    ))}
                  </td>
                  <div className="w-1/4">{data.isCorrect}</div>
                </tr>
                <div className="flex m-3">
                  <Link className="font-medium m-3 text-blue-600 dark:text-blue-500 hover:underline">
                    Sửa
                  </Link>
                  <p className="m-3"> | </p>
                  <Link className="font-medium m-3 text-red-600 dark:text-red-500 hover:underline">
                    Xóa
                  </Link>
                </div>
              </tbody>
            </div>
          </table>
        </div>
      ))}
    </div>
  );
}

export default QuestionItem;
