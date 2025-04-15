import { createEditor,$getRoot, $createParagraphNode,$createTextNode } from 'lexical';
import { registerPlainText } from '@lexical/plain-text';

// 에디터 초기화 함수
function initializeEditor() {
    const editor = createEditor({
        namespace: 'WebpackEditor',
        onError: (error) => console.error(error),
    });

    // 플레인 텍스트 노드 등록
    registerPlainText(editor);

    // 에디터 컨테이너에 연결
    const contentEditableElement = document.getElementById('editor-container');
    editor.setRootElement(contentEditableElement);

    // 초기 콘텐츠 설정
    editor.update(() => {
        const root = $getRoot();
        const paragraph = $createParagraphNode();
        paragraph.append(
            $createTextNode('Webpack으로 번들링된 Lexical 에디터입니다!')
        );
        root.append(paragraph);
    });

    return editor;
}

// DOM이 로드된 후 에디터 초기화
document.addEventListener('DOMContentLoaded', initializeEditor);