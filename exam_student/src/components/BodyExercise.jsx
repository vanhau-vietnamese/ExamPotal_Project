import { Link } from 'react-router-dom';
import { router } from '~/routes';

export default function BodyExercise() {
  return (
    <div>
      <div className="m-3 border border-gray bg-slate-200 rounded-md w-80 max-h-fit drop-shadow-lg">
        <div className="p-5">
          <p className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
            Đây là tiêu đề
          </p>
          <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
            Thời gian làm bài: 30p
          </p>
          <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
            Số lượng câu hỏi: 30 câu
          </p>
          <div className="block">
            <Link
              to={router.detailexam}
              className=" mr-5 max-w-fit inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-green-700 rounded-lg hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              Chi tiết
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
