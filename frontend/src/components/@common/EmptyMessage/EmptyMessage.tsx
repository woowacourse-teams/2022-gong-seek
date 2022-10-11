import * as S from '@/components/@common/EmptyMessage/EmptyMessage.styles';

const EmptyMessage = ({ children }: { children: string }) => (
	<S.Container>
		<S.EmptyImg>🗑</S.EmptyImg>
		<S.EmptyDescription>{children}</S.EmptyDescription>
	</S.Container>
);

export default EmptyMessage;
