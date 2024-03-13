import { Link } from 'react-router-dom';
import { TestData } from '~/TestData';
import { router } from '~/routes';

export default function BodyExercise() {
  return (
    <div className="m-5">
      <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
        <table className="w-full text-[16px] text-left table-auto rtl:text-right text-gray-500">
          <thead className="text-gray-700 uppercase">
            <tr>
              <th scope="col" className="px-6 py-3  w-5/12">
                Danh sách bài tập
              </th>
              <th scope="col" className="px-6 py-3 w-2/12">
                Ngày tạo
              </th>
              <th scope="col" className="px-6 py-3  w-2/12">
                Số câu hỏi
              </th>
              <th scope="col" className="px-6 py-3"></th>
              <th scope="col" className="px-6 py-3"></th>
            </tr>
          </thead>
          <tbody>
            {TestData.map((item) => (
              <tr
                key={item.id}
                className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50"
              >
                <td className=" px-6 py-4 break-all">{item.tittel}</td>
                <td className="px-6 py-4">{item.date}</td>
                <td className="px-6 py-4">{item.number}</td>
                <td className="px-6 py-4 text-right">
                  <Link
                    to={router.detailexam}
                    className="font-medium text-yellow-600 dark:text-yellow-500 hover:underline"
                  >
                    Chi tiết
                  </Link>
                </td>

                <td className="px-6 py-4 text-right">
                  <Link className="font-medium text-red-600 dark:text-red-500 hover:underline">
                    Xóa
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
