import { BiCommentDetail } from 'react-icons/bi';

import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;

	width: 90%;
	height: ${({ theme }) => theme.size.SIZE_126};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	background-color: ${({ theme }) => theme.colors.GRAY_100};

	padding: ${({ theme }) => theme.size.SIZE_006};

	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const CategoryName = styled.div<{ isQuestion: boolean }>`
	width: fit-content;
	height: fit-content;

	color: ${({ theme }) => theme.colors.WHITE};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${(props) => (props.isQuestion ? '#FF0063' : '#3AB0FF')};

	padding: ${({ theme }) => theme.size.SIZE_004};
`;

export const ArticleTitle = styled.div`
	height: ${({ theme }) => theme.size.SIZE_050};
	font-size: ${({ theme }) => theme.size.SIZE_016};

	overflow: hidden;
	word-break: break-all;
	white-space: nowrap;
	text-overflow: ellipsis;

	padding: ${({ theme }) => theme.size.SIZE_002};
`;

export const ArticleSubInfo = styled.div`
	display: flex;
	justify-content: space-between;
`;

export const ArticleTime = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.BLACK_400};
`;

export const ArticleRightBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const CommentBox = styled.div`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_006};
`;
export const CommentIcon = styled(BiCommentDetail)``;
export const CommentCount = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;

export const Views = styled.span`
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;
