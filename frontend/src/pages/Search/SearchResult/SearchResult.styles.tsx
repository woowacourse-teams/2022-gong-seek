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
export const SearchResultBox = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;

	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_020};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		display: grid;
		width: 100%;
		grid-template-columns: 1fr 1fr;
		place-items: center;
		margin: 0 auto;
		gap: ${({ theme }) => theme.size.SIZE_022};
		margin-top: ${({ theme }) => theme.size.SIZE_040};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: 100%;
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		place-items: center;
		margin: 0 auto;
		gap: ${({ theme }) => theme.size.SIZE_022};
		margin-top: ${({ theme }) => theme.size.SIZE_040};
	}
`;
