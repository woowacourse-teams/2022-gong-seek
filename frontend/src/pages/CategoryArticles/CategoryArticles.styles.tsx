import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_024};

	width: 85%;

	margin: ${({ theme }) => theme.size.SIZE_020} auto;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: 100%;
	}
`;

export const CategoryArticlesTitle = styled.h2<{ category: string }>`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	font-weight: 600;

	color: ${({ theme, category }) =>
		category === 'discussion' ? theme.colors.BLUE_500 : theme.colors.RED_500};
`;

export const ArticleItemList = styled.div`
	display: flex;

	flex-direction: column;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_024};

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
		display: grid;
		width: 100%;
		grid-template-columns: 1fr 1fr 1fr;
		place-items: center;
		margin: 0 auto;
		gap: ${({ theme }) => theme.size.SIZE_022};
		margin-top: ${({ theme }) => theme.size.SIZE_040};
	}
`;

export const TitleBox = styled.div`
	display: flex;

	align-items: center;
	justify-content: space-between;
`;

export const EmptyText = styled.div`
	margin: 0 auto;
	font-size: ${({ theme }) => theme.size.SIZE_024};
`;
