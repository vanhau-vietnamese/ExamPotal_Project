import { TestData } from '~/TestData';
import FeatureQuestion from './FeatureQuestion';

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
                <FeatureQuestion></FeatureQuestion>
              </tbody>
            </div>
          </table>
        </div>
      ))}
    </div>
  );
}

export default QuestionItem;
