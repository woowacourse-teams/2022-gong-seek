import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const CategoryTitleContainer = styled.div`
	display: flex;
	position: relative;

	justify-content: space-between;
	align-items: center;

	margin-top: ${({ theme }) => theme.size.SIZE_050};

	z-index: ${({ theme }) => theme.zIndex.CATEGORY_TITLE_CONTAINER};
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
