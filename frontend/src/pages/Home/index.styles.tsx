import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const CategoryTitleContainer = styled.div`
	display: flex;

	justify-content: space-between;
	align-items: center;

	margin-top: ${({ theme }) => theme.size.SIZE_050};
`;

export const CategoryTitleBox = styled.div`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_012};
	align-items: center;
`;

export const CategoryTitle = styled.div<{ isActive: boolean }>`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	font-weight: 600;

	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.3;

	cursor: pointer;

	${({ isActive, theme }) =>
		isActive &&
		css`
			color: ${theme.colors.PURPLE_500};
			text-decoration: underline;
			opacity: 1;
		`}
`;

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	justify-content: center;

	padding: ${({ theme }) => theme.size.SIZE_010};
`;

export const PopularArticleTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	font-weight: 600;

	color: ${({ theme }) => theme.colors.BLACK_600};

	margin-bottom: ${({ theme }) => theme.size.SIZE_030};
`;

export const ArticleItemList = styled.div`
	display: flex;

	flex-direction: column;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_018};

	width: 100%;

	margin-top: ${({ theme }) => theme.size.SIZE_024};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		display: grid;
		width: 90%;
		grid-template-columns: 1fr 1fr 1fr 1fr;
		place-items: center;
		margin: 0 auto;
		gap: ${({ theme }) => theme.size.SIZE_022};
		margin-top: ${({ theme }) => theme.size.SIZE_040};
	}
`;
