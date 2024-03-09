import { EditorContent, useEditor } from '@tiptap/react';
import PropTypes from 'prop-types';
import { useCallback } from 'react';
import * as Icons from './Icons';
import ToolBarButton from './ToolBarButton';
import extensions from './extensions';

TextEditor.propTypes = {
  data: PropTypes.string,
};

export default function TextEditor({ data }) {
  const editor = useEditor({
    extensions,
    content: data,
  });

  const toggleBold = useCallback(() => {
    editor.chain().focus().toggleBold().run();
  }, [editor]);

  const toggleUnderline = useCallback(() => {
    editor.chain().focus().toggleUnderline().run();
  }, [editor]);

  const toggleItalic = useCallback(() => {
    editor.chain().focus().toggleItalic().run();
  }, [editor]);

  const toggleStrike = useCallback(() => {
    editor.chain().focus().toggleStrike().run();
  }, [editor]);

  const toggleCode = useCallback(() => {
    editor.chain().focus().toggleCode().run();
  }, [editor]);

  if (!editor) {
    return null;
  }

  return (
    <div className="relative w-full mb-12">
      <div className="absolute top-[2px] left-[2px] z-10 flex items-center gap-2 w-[calc(100%-4px)] h-10 m-0 px-2 py-0 rounded-ss rounded-se bg-strike">
        <ToolBarButton
          icon={<Icons.RotateLeft />}
          onClick={() => editor.chain().focus().undo().run()}
        />

        <ToolBarButton
          icon={<Icons.RotateRight />}
          onClick={() => editor.chain().focus().redo().run()}
          disabled={!editor.can().redo()}
        />

        <ToolBarButton
          icon={<Icons.HeadingOne />}
          isActive={editor.isActive('heading', { level: 1 })}
          onClick={() => editor.chain().focus().toggleHeading({ level: 1 }).run()}
        />

        <ToolBarButton
          icon={<Icons.HeadingTwo />}
          isActive={editor.isActive('heading', { level: 2 })}
          onClick={() => editor.chain().focus().toggleHeading({ level: 2 }).run()}
        />

        <ToolBarButton
          icon={<Icons.HeadingThree />}
          isActive={editor.isActive('heading', { level: 3 })}
          onClick={() => editor.chain().focus().toggleHeading({ level: 3 }).run()}
        />

        <ToolBarButton
          icon={<Icons.Bold />}
          isActive={editor.isActive('bold')}
          onClick={toggleBold}
        />

        <ToolBarButton
          icon={<Icons.Underline />}
          isActive={editor.isActive('underline')}
          onClick={toggleUnderline}
        />
        <ToolBarButton
          icon={<Icons.Italic />}
          isActive={editor.isActive('italic')}
          onClick={toggleItalic}
        />

        <ToolBarButton
          icon={<Icons.Strikethrough />}
          isActive={editor.isActive('strike')}
          onClick={toggleStrike}
        />

        <ToolBarButton
          icon={<Icons.TextAlignLeft />}
          isActive={editor.isActive({ textAlign: 'left' })}
          onClick={() => editor.chain().focus().setTextAlign('left').run()}
        />

        <ToolBarButton
          icon={<Icons.TextAlignCenter />}
          isActive={editor.isActive({ textAlign: 'center' })}
          onClick={() => editor.chain().focus().setTextAlign('center').run()}
        />

        <ToolBarButton
          icon={<Icons.TextAlignRight />}
          isActive={editor.isActive({ textAlign: 'right' })}
          onClick={() => editor.chain().focus().setTextAlign('right').run()}
        />

        <ToolBarButton
          icon={<Icons.Code />}
          isActive={editor.isActive('code')}
          onClick={toggleCode}
        />
      </div>

      <EditorContent editor={editor} />
    </div>
  );
}
