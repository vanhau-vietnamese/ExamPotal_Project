import { TestData } from '~/TestData';
import FeatureQuestion from './FeatureQuestion';
import { OptionItem } from '~/layouts/components';

function QuestionItem() {
  return (
    <div className="p-3">
      {TestData.map((item) => (
        <div key={item.id} className="border border-gray bg-white m-1 rounded-md shadow-md w-80%">
          <h2 className="text-sm font-semibold ml-5 mt-5">{item.question}</h2>
          <ul className="ml-4 space-y-2">
            <li>
              <OptionItem options={item.content} />
              <label className="flex items-center mb-5">
                <div className="flex">
                  <span className="font-bold text-green-700">{item.isCorrect}</span>
                </div>
              </label>
              <FeatureQuestion></FeatureQuestion>
            </li>
          </ul>
        </div>
      ))}
    </div>
  );
}

export default QuestionItem;