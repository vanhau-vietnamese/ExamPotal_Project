import { Link } from 'react-router-dom';
import { DetailExamItem, Header } from '~/layouts/components';
import { router } from '~/routes';

export default function DetailExam() {
  return (
    <main className="block">
      <Header></Header>
      <div className=" p-5 font-bold text-[26px]">
        <Link to={router.admin}>Quay lại</Link>
      </div>

      <div className="flex">
        <div className="bg-gray-100 m-3 ml-9 rounded-lg flex-1 overflow-hidden">
          <div className="flex flex-col h-full">
            <DetailExamItem></DetailExamItem>
          </div>
        </div>

        <div className="mt-3 bg-gray-100 rounded-lg w-1/5 mr-9 h-96 shadow-md">
          <div className="m-5">
            <h3>Thông tin cơ bản</h3>
            <h4>Người tạo: </h4>
            <h4>Ngôn ngữ: </h4>
            <h4>Số lượng câu hỏi: </h4>
            <h4>Ngày tạo: </h4>
          </div>
        </div>
      </div>
    </main>
  );
}
