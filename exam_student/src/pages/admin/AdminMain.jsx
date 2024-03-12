import ExamList from '../ExamList';

import { Editor } from '~/components';

function AdminMain() {
  return (
    <div>
      <ExamList></ExamList>

      <p>Admin Main Page</p>
      <Editor data={''} onChange={(data) => console.log(data)} />
    </div>
  );
}

export default AdminMain;
