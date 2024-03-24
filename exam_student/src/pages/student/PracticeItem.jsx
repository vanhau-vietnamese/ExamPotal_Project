import { Link } from 'react-router-dom';
import Bookmark from '~/assets/icons/Bookmark';
import Clock from '~/assets/icons/Clock';
import Question from '~/assets/icons/Question';
import { router } from '~/routes';

export default function PracticeItem() {
  return (
    <Link to={router.practice}>
      <div className="flex max-w-[250px] items-center justify-between p-2 m-3 rounded-lg shadow-md bg-white hover:shadow-lg hover:scale-105 transition-transform duration-300">
        <div className="flex items-center space-x-4">
          <div>
            <Bookmark />
          </div>
          <div className="text-sm">
            <h3 className=" text-[16px] font-semibold">
              Tiêu đề bài tập Tiêu đề bài tập Tiêu đề bài tập
            </h3>
            <p>Thông tin cơ bản</p>
            <p className="flex">
              <Clock />
            </p>
            <p>
              <Question />
            </p>
          </div>
        </div>
      </div>
    </Link>
  );
}
