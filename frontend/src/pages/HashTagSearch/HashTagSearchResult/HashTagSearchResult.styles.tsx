import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
	height: fit-content;

	padding: ${({ theme }) => theme.size.SIZE_010};
	justify-content: center;
	align-items: center;
`;

export const Title = styled.div`
	width: 100%;
	margin: ${({ theme }) => theme.size.SIZE_030} 0;
`;

export const EmptyMsg = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;

export const SearchResult = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;
	height: fit-content;

	gap: ${({ theme }) => theme.size.SIZE_014};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		display: grid;
		width: 100%;
		grid-template-columns: 1fr 1fr 1fr;
		place-items: center;
		margin: 0 auto;
		gap: ${({ theme }) => theme.size.SIZE_022};
		margin-top: ${({ theme }) => theme.size.SIZE_040};
	}
`;
