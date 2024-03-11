import { Editor } from '~/components';

function AdminMain() {
  return (
    <div>
      <p>Admin Main Page</p>
      <Editor data={''} onChange={(data) => console.log(data)} />
    </div>
  );
}

export default AdminMain;
