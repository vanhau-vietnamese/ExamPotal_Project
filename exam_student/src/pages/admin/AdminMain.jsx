import { Editor, TypeLanguges } from '~/components';
import ExamList from '../ExamList';

function AdminMain() {
  return (
    <div>
      <TypeLanguges></TypeLanguges>
      <ExamList></ExamList>

      <p>Admin Main Page</p>
      <Editor data={''} onChange={(data) => console.log(data)} />
    </div>
  );
}

export default AdminMain;
