import { DetailExamItem } from '~/layouts/components';

export default function DetailExam() {
  return (
    <main className="block">
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
