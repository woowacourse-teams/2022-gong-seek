import styled from '@emotion/styled';
import { AiOutlineHeart } from 'react-icons/ai';
import { AiFillHeart } from 'react-icons/ai';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_160};
	box-shadow: 0 8px 24px ${({ theme }) => theme.boxShadows.secondary};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	padding: ${({ theme }) => theme.size.SIZE_016};
`;

export const ArticleItemTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_016};
	font-weight: 600;
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const Views = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;

export const ArticleInfoBox = styled.div`
	display: flex;
	justify-content: space-between;
	margin-top: ${({ theme }) => theme.size.SIZE_008};
`;

export const Content = styled.div`
	margin-top: ${({ theme }) => theme.size.SIZE_016};
	font-size: ${({ theme }) => theme.size.SIZE_012};

	text-overflow: ellipsis;
	overflow: hidden;

	line-height: ${({ theme }) => theme.size.SIZE_016};
`;

export const UserProfile = styled.img`
	width: ${({ theme }) => theme.size.SIZE_032};
	height: ${({ theme }) => theme.size.SIZE_032};
	object-fit: cover;
	object-position: center;
	border-radius: 50%;
`;

export const ProfileBox = styled.div`
	display: flex;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_012};
`;

export const FooterBox = styled.div`
	display: flex;
	align-items: center;
	margin-top: auto;
`;

export const EmptyHeart = styled(AiOutlineHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_024};
`;

export const FillHeart = styled(AiFillHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_024};
	color: ${({ theme }) => theme.colors.RED_600};
`;

export const HeartBox = styled.div`
	display: flex;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const ArticleTimeStamp = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_012};
`;

export const RightFooterBox = styled.div`
	display: flex;
	margin-left: auto;
	gap: ${({ theme }) => theme.size.SIZE_012};
	align-items: center;
`;
