import { Link } from 'react-router-dom';
import { router } from '~/routes';

function Sidebar() {
  return (
    <div className="bg-gray-100 font-family-karla block w-72 max-h-[500px] shadow-lg">
      <div className="flex">
        <div>
          <div className="mt-6 w-72 text-2xl shadow-lg font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
            <Link
              to={router.admin}
              className="block w-full px-4 py-2 rounded-b-lg cursor-pointer hover:bg-gray-100 hover:text-green-700 focus:outline-none focus:ring-2 focus:ring-green-700 focus:text-white-700 "
            >
              Kho bài tập
            </Link>
            <Link
              to={router.questionlist}
              className="block w-full px-4 py-2 border-b border-gray-200 cursor-pointer hover:bg-gray-100 hover:text-green-700 focus:outline-none focus:ring-2 focus:ring-green-700 focus:text-white-700 "
            >
              Kho câu hỏi
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
