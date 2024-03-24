import { DetailExamItem } from '../admin/Exam';
import SidebarPractice from './SidebarPractice';

export default function StartPractice() {
  return (
    <div className="w-full">
      <div className="">
        <SidebarPractice></SidebarPractice>
      </div>

      <div className="max-w-[700px] ">
        <DetailExamItem></DetailExamItem>
      </div>
    </div>
  );
}
