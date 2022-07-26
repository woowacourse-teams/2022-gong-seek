import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_024};

	width: 85%;

	margin: ${({ theme }) => theme.size.SIZE_020} auto;
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
`;

export const TitleBox = styled.div`
	display: flex;

	align-items: center;
	justify-content: space-between;
`;
