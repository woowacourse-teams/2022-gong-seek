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

export const TitleBox = styled.div`
	display: flex;

	align-items: center;
	justify-content: space-between;
	z-index: ${({ theme }) => theme.zIndex.CATEGORY_TITLE_CONTAINER};
`;
