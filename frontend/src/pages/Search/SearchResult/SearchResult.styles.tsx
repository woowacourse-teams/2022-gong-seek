import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100vw;

	padding: ${({ theme }) => theme.size.SIZE_010};

	gap: ${({ theme }) => theme.size.SIZE_020};
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
`;

//TODO: 여기있는 media query 홈, 카테고리별 게시물 읽기에서 똑같이 사용됨.
export const SearchResultBox = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;

	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_020};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		width: 100%;
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		place-items: center;
		margin: 0 auto;
		gap: ${({ theme }) => theme.size.SIZE_022};
		margin-top: ${({ theme }) => theme.size.SIZE_040};
	}
`;
