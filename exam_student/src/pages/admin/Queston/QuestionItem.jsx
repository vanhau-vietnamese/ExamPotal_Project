import { Link } from 'react-router-dom';
import { TestData } from '~/TestData';

function QuestionItem() {
  return (
    <div>
      {TestData.map((data) => (
        <div key={data.id} className="p-5">
          <table className="w-full">
            <div className="bg-green-50 rounded-md shadow-md">
              <tbody>
                <tr className="block m-3 text-sm">
                  <td className="w-3/4">{data.question}</td>
                  <div className="w-1/4">
                    {data.content.map((option, index) => (
                      <span key={index} className="m-3">
                        {option}
                      </span>
                    ))}
                  </div>
                  <div className="w-1/4">{data.isCorrect}</div>
                </tr>
                <div className="flex m-3">
                  <Link className="font-medium m-3 text-green-600  hover:underline">Sửa</Link>
                  <p className="m-3"> | </p>
                  <Link className="font-medium m-3 text-red-600  hover:underline">Xóa</Link>
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
