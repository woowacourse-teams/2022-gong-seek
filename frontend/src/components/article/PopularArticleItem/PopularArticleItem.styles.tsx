import { AiOutlineEye, AiOutlineMessage, AiFillHeart } from 'react-icons/ai';

import {
	UserProfile,
	ArticleItemTitle,
	HashTagListBox,
} from '@/components/article/ArticleItem/ArticleItem.styles';
import { TextOverflow } from '@/styles/mixin';
import { Category } from '@/types/articleResponse';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const CategoryBox = styled.div<{ categoryType: 'question' | 'discussion' }>`
	background-color: white;
	margin-left: auto;
	${({ theme, categoryType }) => css`
		border-radius: ${theme.size.SIZE_006};
		color: ${categoryType === 'question' ? theme.colors.RED_500 : theme.colors.BLUE_500};
		padding: ${theme.size.SIZE_008};
	`}
`;

export const CommentIcon = styled(AiOutlineMessage)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const ViewIcon = styled(AiOutlineEye)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const HeartIcon = styled(AiFillHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.RED_600};
`;

export const IconContainer = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_006};
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;

export const AuthorNameText = styled.div`
	color: white;
`;

export const PopularArticleUserProfile = styled(UserProfile)`
	border: 2px solid transparent;
	background-image: ${({ theme }) => theme.GradientColors.POPULAR_PROFILE_BORDER};
	background-origin: border-box;
	background-clip: content-box, border-box;
`;

export const PopularArticleHeader = styled.div<{ category: Category }>`
	${({ theme, category }) => css`
		background: ${category === 'question' ? theme.colors.RED_500 : theme.colors.BLUE_500};
		opacity: 0.8;
		box-shadow: 0, 0, 0, inset 0 0 4px #5e0080;
		padding: ${theme.size.SIZE_014} ${theme.size.SIZE_012};
		border-top-left-radius: ${theme.size.SIZE_010};
		border-top-right-radius: ${theme.size.SIZE_010};
		margin-bottom: ${theme.size.SIZE_006};
		line-height: ${theme.size.SIZE_024};
	`}

	${TextOverflow}
`;

export const PopularArticleContent = styled.div`
	padding: ${({ theme }) => theme.size.SIZE_014};
`;

export const PopularArticleHashTagListBox = styled(HashTagListBox)`
	margin: ${({ theme }) => theme.size.SIZE_016} 0;
	height: inherit;
`;

export const PopularArticleItemTitle = styled(ArticleItemTitle)`
	display: flex;
	height: ${({ theme }) => theme.size.SIZE_038};

	${TextOverflow}
`;
