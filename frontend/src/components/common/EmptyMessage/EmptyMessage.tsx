import * as S from '@/components/common/EmptyMessage/EmptyMessage.styles';

const EmptyMessage = () => (
	<S.Container>
		<S.EmptyImg>🗑</S.EmptyImg>
		<S.EmptyDescription>게시글이 텅 비었습니다</S.EmptyDescription>
	</S.Container>
);

export default EmptyMessage;
